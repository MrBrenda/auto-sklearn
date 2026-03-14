# Model Evaluation

Skill for rigorous model evaluation and result interpretation in AutoML research.

## When to Use
When the user wants to evaluate auto-sklearn models, analyze ensemble compositions, or interpret AutoML results for research purposes.

## Instructions

1. **Performance Evaluation**: Compute comprehensive metrics.
   ```python
   from autosklearn.metrics import (
       accuracy, balanced_accuracy, f1, f1_macro, f1_micro, f1_weighted,
       precision, recall, log_loss, roc_auc, mean_squared_error, r2
   )

   # For classification
   print(automl.sprint_statistics())
   y_pred = automl.predict(X_test)
   y_prob = automl.predict_proba(X_test)

   # Multiple metrics
   from sklearn.metrics import classification_report, confusion_matrix
   print(classification_report(y_test, y_pred))
   ```

2. **Ensemble Analysis**: Understand the ensemble composition.
   ```python
   # Show selected models and their weights
   print(automl.show_models())

   # Analyze ensemble diversity
   # Check which algorithm families are represented
   # Examine hyperparameter configurations of ensemble members
   ```
   - Visualize ensemble weights distribution
   - Analyze algorithm family diversity in the ensemble
   - Compare ensemble vs. best single model performance

3. **Search Space Analysis**: Analyze the AutoML search process.
   - Plot validation performance over time (anytime performance)
   - Analyze which configurations were explored
   - Examine the SMAC optimization trajectory
   - Check metalearning warm-start effectiveness
   - Review the `tmp_folder` for detailed run history

4. **Ablation Studies**: Evaluate component contributions.
   - Compare with/without metalearning initialization
   - Compare with/without ensemble selection
   - Evaluate impact of time budget on final performance
   - Test restricted search spaces (e.g., only tree-based methods)
   ```python
   # Example: disable metalearning
   automl_no_meta = AutoSklearnClassifier(
       initial_configurations_via_metalearning=0
   )

   # Example: disable ensembles
   automl_no_ensemble = AutoSklearnClassifier(
       ensemble_size=1
   )
   ```

5. **Statistical Validation**: Ensure results are statistically sound.
   - Report confidence intervals using bootstrap
   - Use paired statistical tests for model comparison
   - Apply multiple comparison corrections (Bonferroni, Holm)
   - Assess result stability across different random seeds
   - Report both mean and median performance with variance

6. **Interpretability**: Help interpret AutoML results.
   - Feature importance from the best model or ensemble
   - Partial dependence plots for key features
   - SHAP values for model explanations
   - Learning curves to assess data sufficiency
